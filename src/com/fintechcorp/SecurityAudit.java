package com.fintechcorp;

import com.fintechcorp.annotations.*;

@FileSource(delimiter = ";")
public class SecurityAudit {
    @Column(index = 0, name = "ip")
    @NotNull
    private String ipAddress;

    @Column(index = 1, name = "severity")
    @Regex(pattern = "(LOW | MEDIUM | HIGH | CRITICAL)", message = "Severity must be LOW, MEDIUM, HIGH or CRITICAL")
    private String severity;

    public SecurityAudit() {}

    @Override
    public String toString() { return "SecurityAudit{ip='" + ipAddress + "', severity='" + severity + "'}"; }
}