package com.kizilkaya.kocak.logcatcher.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class JWTUtil {
    private static final String SECRET_KEY = "kizilkaya";

    public static String generateJWT() {
        try {
            String header  = String.join("\n", Files.readAllLines(Paths.get("./src/main/resources/header.json")));
            String payload = String.join("\n", Files.readAllLines(Paths.get("./src/main/resources/payload.json")));
            String part1 = doBASE64(header);
            String part2 = doBASE64(payload);
            String part1Part2 = part1 + "." + part2;

            return part1Part2 + "." + doBASE64(doHMACSHA256(part1Part2));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String doHMACSHA256(String part1AndPart2) {
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(SECRET_KEY.getBytes(), "HmacSHA256"));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }

        assert mac != null;
        byte[] hashBytes = mac.doFinal(part1AndPart2.getBytes());
        return doBASE64(hashBytes);
    }

    public static boolean validateJWT(String jwt) {

        String[] parts = jwt.split("\\.");
        String PART1 = parts[0];
        String PART2 = parts[1];
        String PART3 = parts[2];

        String PART1_PART2 = PART1 + "." + PART2;

        String jwtSignature = doBASE64(doHMACSHA256(PART1_PART2));

        return jwtSignature.equals(PART3);

    }

    private static String doBASE64(byte[] bytes) {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(bytes);
    }

    private static String doBASE64(String input) {
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
        return doBASE64(bytes);
    }


}
