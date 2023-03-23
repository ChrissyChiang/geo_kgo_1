package gov.kcg.kgo.geomodel; 

import org.springframework.stereotype.Component; 
import io.swagger.annotations.ApiModel; 
import io.swagger.annotations.ApiModelProperty; 
import java.io.Serializable; 
import java.util.ArrayList; 
import java.util.List; 
import gov.kcg.kgo.geoentity.GeoKgoLight; 

/** 
 * GEO 20210829 add
 *
 * Model for 高雄路燈資訊
 */

@Component
@ApiModel(description = "高雄路燈資訊")
public class GeoKgoLightModel implements Serializable {

    private static final long serialVersionUID = 1L; 

    @ApiModelProperty(notes = "路燈id")
    private Integer lightId; 

    @ApiModelProperty(notes = "路燈編號")
    private String lightNo; 

    @ApiModelProperty(notes = "路燈行政區名")
    private String lightDistrict; 

    @ApiModelProperty(notes = "路燈地址")
    private String lightAddr; 

    @ApiModelProperty(notes = "路燈緯度座標")
    private String lightLatitude; 

    @ApiModelProperty(notes = "路燈經度座標")
    private String lightLongitude; 


    public Integer getLightId() { 
        return lightId; 
    } 

    public void setLightId(Integer lightId) { 
        this.lightId = lightId; 
    } 

    public String getLightNo() { 
        return lightNo; 
    } 

    public void setLightNo(String lightNo) { 
        this.lightNo = lightNo; 
    } 

    public String getLightDistrict() { 
        return lightDistrict; 
    } 

    public void setLightDistrict(String lightDistrict) { 
        this.lightDistrict = lightDistrict; 
    } 

    public String getLightAddr() { 
        return lightAddr; 
    } 

    public void setLightAddr(String lightAddr) { 
        this.lightAddr = lightAddr; 
    } 

    public String getLightLatitude() { 
        return lightLatitude; 
    } 

    public void setLightLatitude(String lightLatitude) { 
        this.lightLatitude = lightLatitude; 
    } 

    public String getLightLongitude() { 
        return lightLongitude; 
    } 

    public void setLightLongitude(String lightLongitude) { 
        this.lightLongitude = lightLongitude; 
    } 



    public static GeoKgoLightModel changeToModel(GeoKgoLight entity) { 
        GeoKgoLightModel model = new GeoKgoLightModel(); 
        model.setLightId(entity.getLightId()); 
        model.setLightNo(entity.getLightNo()); 
        model.setLightDistrict(entity.getLightDistrict()); 
        model.setLightAddr(entity.getLightAddr()); 
        model.setLightLatitude(entity.getLightLatitude()); 
        model.setLightLongitude(entity.getLightLongitude()); 
        return model; 
    } //changeToModel 

    public static List<GeoKgoLightModel> changeListToModel(List<GeoKgoLight> entityList) { 
        List<GeoKgoLightModel> modelList = null; 
        if (entityList!=null) { 
            modelList = new ArrayList<>(); 
            for (int i=0; i<entityList.size(); i++) { 
                GeoKgoLightModel model = changeToModel(entityList.get(i)); 
                modelList.add(model); 
            } 
        } //if (entityList!=null) 
        return modelList; 
    } //changeListToModel 
} 
