package com.ybcharlog.api.common.util;

import java.util.Base64;

public class JwsUtil {

    public static String decodeJwsPayload(String jws) {
        // JWS decode (header, payload, signature) payload 부분을 decode해서 사용
        final String jwsPayload = jws.split("\\.")[1];
        return new String(Base64.getUrlDecoder().decode(jwsPayload));
    }
}
