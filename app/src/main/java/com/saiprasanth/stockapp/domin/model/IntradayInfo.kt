package com.saiprasanth.stockapp.domin.model

import java.time.LocalDateTime

data class IntradayInfo(
    val date : LocalDateTime,
    val close : Double
)

