package com.tecno_comfenalco.pa.shared.utils.helper;

import org.springframework.http.ResponseEntity;

import com.tecno_comfenalco.pa.shared.utils.result.Result;

public class ResponseEntityHelper {

    static public <T> ResponseEntity<T> toResponseEntity(Result<T, Exception> result, T successBody, T errorBody) {
        if (result == null || !result.isOk()) {
            return ResponseEntity.status(400).body(errorBody);
        }
        return ResponseEntity.ok(successBody);
    }
}
