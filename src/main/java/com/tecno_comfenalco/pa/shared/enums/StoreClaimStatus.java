package com.tecno_comfenalco.pa.shared.enums;

/**
 * Estados del proceso de reclamación de una tienda
 */
public enum StoreClaimStatus {
    /**
     * Tienda registrada por una distribuidora, sin usuario propietario asignado
     */
    PENDING_CLAIM,
    
    /**
     * Tienda reclamada por su propietario después de haber sido registrada por terceros
     */
    CLAIMED,
    
    /**
     * Tienda registrada directamente por su propietario (autogestión)
     */
    SELF_REGISTERED
}
