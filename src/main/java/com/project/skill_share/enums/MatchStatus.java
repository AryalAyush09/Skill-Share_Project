package com.project.skill_share.enums;

public enum MatchStatus {
    PENDING,       // request sent, waiting for action
    CONFIRMED,     // request accepted
    REJECTED,      // request rejected
    CANCELLED,     // request cancelled by sender
    EXPIRED,       // request auto-expired (timeout)
    BLOCKED,       // blocked by one of the users
    IN_PROGRESS,   // skill exchange started
    COMPLETED,     // exchange successfully completed
    FAILED         // confirmed but exchange failed
}
