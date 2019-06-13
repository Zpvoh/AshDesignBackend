package com.web.design.cipher;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.Key;
import java.security.KeyStore;
import java.util.Date;
import java.util.Scanner;

public class JWT {
    private final static String DEFAULT_FILE="./id_rsa";
    private static String key=null;

    public static void readKey(String filename){
        try {
            Scanner scanner=new Scanner(new FileInputStream(filename));
            StringBuilder keyBuilder=new StringBuilder();
            while(scanner.hasNextLine()){
                keyBuilder.append(scanner.nextLine());
            }
            key=keyBuilder.toString();
        } catch (FileNotFoundException e) {
            System.out.println("There is no key file.");
        }
    }

    public static String createJWT(String id, String issuer, String subject, long ttlMillis) {
        if(key==null)
            readKey(DEFAULT_FILE);

//The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

//We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

//if it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

//Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    public static Claims parseJWT(String jwt) {
        if(key==null)
            readKey(DEFAULT_FILE);
//This line will throw an exception if it is not a signed JWS (as expected)
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(key))
                .parseClaimsJws(jwt).getBody();
        System.out.println("ID: " + claims.getId());
        System.out.println("Subject: " + claims.getSubject());
        System.out.println("Issuer: " + claims.getIssuer());
        System.out.println("Expiration: " + claims.getExpiration());
        return claims;
    }
}
