package com.zy_admin.util;

import org.springframework.beans.factory.annotation.Autowired;

import java.security.SecureRandom;

/**
 * @author fangqian
 */
public class SnowflakeManager {

    private static final long EPOCH_STAMP = 1262275200000L;
    private static final long SEQUENCE_BIT = 12L;
    private static final long MACHINE_BIT = 5L;
    private static final long DATA_CENTER_BIT = 5L;
    private static final long MAX_SEQUENCE_NUM = -1L ^ (-1L << SEQUENCE_BIT);
    private static final long MAX_MACHINE_NUM = -1L ^ (-1L << MACHINE_BIT);
    private static final long MAX_DATA_CENTER_NUM = -1L ^ (-1L << DATA_CENTER_BIT);
    private static final long MACHINE_LEFT = SEQUENCE_BIT;
    private static final long DATA_CENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private static final long TIMESTAMP_LEFT = SEQUENCE_BIT + MACHINE_BIT + DATA_CENTER_BIT;
    private final long machineId;
    private final long dataCenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public SnowflakeManager(long machineId, long dataCenterId) {
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException(String.format("machine id can't be greater than %d or less than 0", MAX_MACHINE_NUM));
        }
        if (dataCenterId > MAX_DATA_CENTER_NUM || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("data center id can't be greater than %d or less than 0", MAX_DATA_CENTER_NUM));
        }
        this.machineId = machineId;
        this.dataCenterId = dataCenterId;
    }

    public synchronized long nextId() throws Exception {
        String os = System.getProperty("os.name");
        SecureRandom secureRandom;
        if (os.toLowerCase().startsWith("win")) {
            // windows机器用
            secureRandom = SecureRandom.getInstanceStrong();
        } else {
            // linux机器用
            secureRandom = SecureRandom.getInstance("NativePRNGNonBlocking");
        }
        //SecureRandom secureRandom = SecureRandom.getInstanceStrong();
        long currentTimeMillis = this.currentTimeMillis();
        if (currentTimeMillis < this.lastTimestamp) {
            throw new Exception(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", (this.lastTimestamp - currentTimeMillis)));
        }

        if (this.lastTimestamp == currentTimeMillis) {
            this.sequence = (this.sequence + 1) & MAX_SEQUENCE_NUM;
            if (this.sequence == 0) {
                this.sequence = secureRandom.nextInt(Long.valueOf(SEQUENCE_BIT).intValue());
                currentTimeMillis = this.tilNextMillis(this.lastTimestamp);
            }
        } else {
            this.sequence = secureRandom.nextInt(Long.valueOf(SEQUENCE_BIT).intValue());
        }
        this.lastTimestamp = currentTimeMillis;

        // 64 Bit ID (42(Millis)+5(Data Center ID)+5(Machine ID)+12(Repeat Sequence Summation))
        long nextId = ((currentTimeMillis - EPOCH_STAMP) << TIMESTAMP_LEFT)
                | (this.dataCenterId << DATA_CENTER_LEFT)
                | (this.machineId << MACHINE_LEFT)
                | this.sequence;

        return nextId;
    }

    private long tilNextMillis(long lastTimestamp) {
        long currentTimeMillis = this.currentTimeMillis();
        while (currentTimeMillis <= lastTimestamp) {
            currentTimeMillis = this.currentTimeMillis();
        }
        return currentTimeMillis;
    }

    private long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}