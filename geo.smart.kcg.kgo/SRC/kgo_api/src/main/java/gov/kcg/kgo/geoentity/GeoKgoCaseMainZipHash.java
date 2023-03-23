package gov.kcg.kgo.geoentity;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * GEO 20221009 add_Jim
 * MyDataZipHash
 */
@Entity
@DynamicInsert
@Table(name = "GEO_KGO_CASE_MAIN_ZIP_HASH", schema = "dbo")
public class GeoKgoCaseMainZipHash implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "TxId", columnDefinition = "varchar(36) NOT NULL")
    private String txId;
    @Column(name = "ZipHash")
    private String zipHash;
    @Column(name = "MixHash")
    private String mixHash;

    public GeoKgoCaseMainZipHash() {
    }

    public GeoKgoCaseMainZipHash(String txId, String zipHash, String mixHash) {
        this.txId = txId;
        this.zipHash = zipHash;
        this.mixHash = mixHash;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public String getZipHash() {
        return zipHash;
    }

    public void setZipHash(String zipHash) {
        this.zipHash = zipHash;
    }

    public String getMixHash() {
        return mixHash;
    }

    public void setMixHash(String mixHash) {
        this.mixHash = mixHash;
    }
}
