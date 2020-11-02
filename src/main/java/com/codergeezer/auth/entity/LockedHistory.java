package com.codergeezer.auth.entity;

import com.codergeezer.common.data.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

/**
 * @author haidv
 * @version 1.0
 */
@Data
@Entity
@Table(name = "locked_history")
@EqualsAndHashCode(callSuper = true)
public class LockedHistory extends BaseEntity {

    @Id
    @Column(name = "locked_history_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lockedHistoryId;

    @Column(name = "user_name")
    private String username;

    @Column(name = "locked_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lockedTime;

    @Column(name = "is_locked")
    private Boolean isLocked;

    @Column(name = "unlocked_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date unlockedTime;

    @Column(name = "account_unlocked")
    private String accountUnlocked;
}
