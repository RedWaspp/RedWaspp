package com.ford.cvas.evap.purge.common;

import java.time.LocalDateTime;

public interface TimedEvent {
    LocalDateTime getTime();
}
