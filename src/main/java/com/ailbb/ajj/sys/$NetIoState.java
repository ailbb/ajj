package com.ailbb.ajj.sys;

import com.ailbb.ajj.$;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class $NetIoState {
    private static final long serialVersionUID = 1L;
    private String id;

    private String name;
    private String hostname;
    private String rxpck;
    private String txpck;
    private String rxbyt;
    private String txbyt;
    private long dropin;
    private long dropout;
    private String netConnections;
    private String dateStr;
    private Date createTime;
    private String errorin;
    private String errorout;


    private long receivedBytes;
    private long sendBytes;

    private long receivedNonUnicastPackets;
    private long sendNonUnicastPackets;

    private long receivedUnicastPackets;
    private long sendUnicastPackets;

    private long receivedDiscards;
    private long sendDiscards;

    private long receivedErrors;
    private long sendErrors;

    private long receivedUnknownProtocols;

    private double loss = 100;
    private double delay = 9999;
    private int status = -1;

    private String testIp = "";

    public $NetIoState(){
        this.id = $.snowflakeIdStr();
    }

    public double getLoss() {
        return loss;
    }

    public void setLoss(double loss) {
        this.loss = loss;
    }

    public double getDelay() {
        return delay;
    }

    public void setDelay(double delay) {
        this.delay = delay;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTestIp() {
        return testIp;
    }

    public void setTestIp(String testIp) {
        this.testIp = testIp;
    }

    public long getReceivedBytes() {
        return receivedBytes;
    }

    public void setReceivedBytes(long receivedBytes) {
        this.receivedBytes = receivedBytes;
    }

    public long getSendBytes() {
        return sendBytes;
    }

    public void setSendBytes(long sendBytes) {
        this.sendBytes = sendBytes;
    }

    public long getReceivedNonUnicastPackets() {
        return receivedNonUnicastPackets;
    }

    public void setReceivedNonUnicastPackets(long receivedNonUnicastPackets) {
        this.receivedNonUnicastPackets = receivedNonUnicastPackets;
    }

    public long getSendNonUnicastPackets() {
        return sendNonUnicastPackets;
    }

    public void setSendNonUnicastPackets(long sendNonUnicastPackets) {
        this.sendNonUnicastPackets = sendNonUnicastPackets;
    }

    public long getReceivedUnicastPackets() {
        return receivedUnicastPackets;
    }

    public void setReceivedUnicastPackets(long receivedUnicastPackets) {
        this.receivedUnicastPackets = receivedUnicastPackets;
    }

    public long getSendUnicastPackets() {
        return sendUnicastPackets;
    }

    public void setSendUnicastPackets(long sendUnicastPackets) {
        this.sendUnicastPackets = sendUnicastPackets;
    }

    public long getReceivedDiscards() {
        return receivedDiscards;
    }

    public void setReceivedDiscards(long receivedDiscards) {
        this.receivedDiscards = receivedDiscards;
    }

    public long getSendDiscards() {
        return sendDiscards;
    }

    public void setSendDiscards(long sendDiscards) {
        this.sendDiscards = sendDiscards;
    }

    public long getReceivedErrors() {
        return receivedErrors;
    }

    public void setReceivedErrors(long receivedErrors) {
        this.receivedErrors = receivedErrors;
    }

    public long getSendErrors() {
        return sendErrors;
    }

    public void setSendErrors(long sendErrors) {
        this.sendErrors = sendErrors;
    }

    public long getReceivedUnknownProtocols() {
        return receivedUnknownProtocols;
    }

    public void setReceivedUnknownProtocols(long receivedUnknownProtocols) {
        this.receivedUnknownProtocols = receivedUnknownProtocols;
    }

    public String getName() {
        return name;
    }

    public $NetIoState setName(String name) {
        this.name = name;
        return this;
    }

    public String getRxpck() {
        return this.rxpck;
    }

    public void setRxpck(String rxpck) {
        this.rxpck = rxpck;
    }

    public String getTxpck() {
        return this.txpck;
    }

    public void setTxpck(String txpck) {
        this.txpck = txpck;
    }

    public String getRxbyt() {
        return this.rxbyt;
    }

    public void setRxbyt(String rxbyt) {
        this.rxbyt = rxbyt;
    }

    public String getTxbyt() {
        return this.txbyt;
    }

    public void setTxbyt(String txbyt) {
        this.txbyt = txbyt;
    }

    public long getDropin() {
        return this.dropin;
    }

    public void setDropin(long dropin) {
        this.dropin = dropin;
    }

    public long getDropout() {
        return this.dropout;
    }

    public void setDropout(long dropout) {
        this.dropout = dropout;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDateStr() {
        return this.dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getHostname() {
        return this.hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getNetConnections() {
        if (StringUtils.isEmpty((CharSequence)this.netConnections)) {
            this.netConnections = "0";
        }
        return this.netConnections;
    }

    public void setNetConnections(String netConnections) {
        this.netConnections = netConnections;
    }

    public String getErrorin() {
        return errorin;
    }

    public $NetIoState setErrorin(String errorin) {
        this.errorin = errorin;
        return this;
    }

    public String getErrorout() {
        return errorout;
    }

    public $NetIoState setErrorout(String errorout) {
        this.errorout = errorout;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("$NetIoState{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", hostname='").append(hostname).append('\'');
        sb.append(", rxpck='").append(rxpck).append('\'');
        sb.append(", txpck='").append(txpck).append('\'');
        sb.append(", rxbyt='").append(rxbyt).append('\'');
        sb.append(", txbyt='").append(txbyt).append('\'');
        sb.append(", dropin=").append(dropin);
        sb.append(", dropout=").append(dropout);
        sb.append(", netConnections='").append(netConnections).append('\'');
        sb.append(", dateStr='").append(dateStr).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", errorin='").append(errorin).append('\'');
        sb.append(", errorout='").append(errorout).append('\'');
        sb.append(", receivedBytes=").append(receivedBytes);
        sb.append(", sendBytes=").append(sendBytes);
        sb.append(", receivedNonUnicastPackets=").append(receivedNonUnicastPackets);
        sb.append(", sendNonUnicastPackets=").append(sendNonUnicastPackets);
        sb.append(", receivedUnicastPackets=").append(receivedUnicastPackets);
        sb.append(", sendUnicastPackets=").append(sendUnicastPackets);
        sb.append(", receivedDiscards=").append(receivedDiscards);
        sb.append(", sendDiscards=").append(sendDiscards);
        sb.append(", receivedErrors=").append(receivedErrors);
        sb.append(", sendErrors=").append(sendErrors);
        sb.append(", receivedUnknownProtocols=").append(receivedUnknownProtocols);
        sb.append(", loss=").append(loss);
        sb.append(", delay=").append(delay);
        sb.append(", status=").append(status);
        sb.append(", testIp='").append(testIp).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

