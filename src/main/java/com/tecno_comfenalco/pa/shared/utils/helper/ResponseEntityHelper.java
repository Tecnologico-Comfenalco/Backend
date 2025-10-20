package com.tecno_comfenalco.pa.shared.utils.helper;

import org.springframework.http.ResponseEntity;

import com.tecno_comfenalco.pa.shared.utils.result.Result;

public class ResponseEntityHelper {

    static public <T> ResponseEntity<T> toResponseEntity(Result<T, Exception> result) {
        if (result == null || !result.isOk()) {
            return (ResponseEntity<T>) ResponseEntity.status(400).body(result != null ? result.getError() : null);
        }
        return ResponseEntity.ok(result.getValue());
    }
}
