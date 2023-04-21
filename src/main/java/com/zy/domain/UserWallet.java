package com.zy.domain;

import lombok.Data;

@Data
public class UserWallet {
    private Integer user_id;
    private Double balance;
    private Long create_time;
    private Long update_time;
    private Integer wallet_status;
}
