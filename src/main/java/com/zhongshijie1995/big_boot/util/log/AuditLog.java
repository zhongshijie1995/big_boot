package com.zhongshijie1995.big_boot.util.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class AuditLog extends Filter<ILoggingEvent> {
    public static final String AUDI_TAG = "[AUDIT]";

    @Override
    public FilterReply decide(ILoggingEvent iLoggingEvent) {
        if (iLoggingEvent.getFormattedMessage().startsWith(AUDI_TAG)) {
            return FilterReply.ACCEPT;
        }
        return FilterReply.DENY;
    }
}
