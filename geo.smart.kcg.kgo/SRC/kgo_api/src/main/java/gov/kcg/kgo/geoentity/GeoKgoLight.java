package gov.kcg.kgo.geoentity; 

import javax.persistence.*; 
import org.hibernate.annotations.DynamicInsert; 
import java.io.Serializable; 

/** 
 * GEO 20210829 add
 *
 * 高雄路燈資訊
 */

@Entity
@DynamicInsert
@Table(name = "GEO_KGO_LIGHT", schema = "dbo")
public class GeoKgoLight implements Serializable {

    private static final long serialVersionUID = 1L; 

    private Integer lightId; //路燈id 
    private String lightNo; //路燈編號 
    private String lightDistrict; //路燈行政區名 
    private String lightAddr; //路燈地址 
    private String lightLatitude; //路燈緯度座標 
    private String lightLongitude; //路燈經度座標 

    @Id 
    @Column(name = "light_id", columnDefinition = "int NOT NULL") 
    public Integer getLightId() { 
        return lightId; 
    } 

    public void setLightId(Integer lightId) { 
        this.lightId = lightId; 
    } 

    @Basic 
    @Column(name = "light_no", columnDefinition = "nvarchar(20)") 
    public String getLightNo() { 
        return lightNo; 
    } 

    public void setLightNo(String lightNo) { 
        this.lightNo = lightNo; 
    } 

    @Basic 
    @Column(name = "light_district", columnDefinition = "nvarchar(20)") 
    public String getLightDistrict() { 
        return lightDistrict; 
    } 

    public void setLightDistrict(String lightDistrict) { 
        this.lightDistrict = lightDistrict; 
    } 

    @Basic 
    @Column(name = "light_addr", columnDefinition = "nvarchar(500)") 
    public String getLightAddr() { 
        return lightAddr; 
    } 

    public void setLightAddr(String lightAddr) { 
        this.lightAddr = lightAddr; 
    } 

    @Basic 
    @Column(name = "light_latitude", columnDefinition = "varchar(20)") 
    public String getLightLatitude() { 
        return lightLatitude; 
    } 

    public void setLightLatitude(String lightLatitude) { 
        this.lightLatitude = lightLatitude; 
    } 

    @Basic 
    @Column(name = "light_longitude", columnDefinition = "varchar(20)") 
    public String getLightLongitude() { 
        return lightLongitude; 
    } 

    public void setLightLongitude(String lightLongitude) { 
        this.lightLongitude = lightLongitude; 
    } 

} 
