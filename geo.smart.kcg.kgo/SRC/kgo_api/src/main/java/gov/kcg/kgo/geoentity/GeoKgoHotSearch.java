package gov.kcg.kgo.geoentity;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * GEO 20220729 add 搜尋引擎切換
 */
@Entity
@DynamicInsert
@Table(name = "GEO_KGO_HOT_SEARCH", schema = "dbo")
public class GeoKgoHotSearch {

    private static final long serialVersionUID = 1L;

    @Id
    @ApiModelProperty(notes = "搜尋引擎序號")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="hot_search_seq")
    private Integer hotSearchSeq;

    @Column(name = "isOpenKgo", columnDefinition = "int not null")
    private Integer isOpenKgo; //切換搜尋引擎

    @Column(name = "edit_user", columnDefinition = "int (10) NOT NULL")
    private String editUser; //編輯者

    @Column(name = "edit_time", columnDefinition = "default current time")
    private Timestamp editTime; //編輯時間

    public Integer getHotSearchSeq() {
        return hotSearchSeq;
    }

    public void setHotSearchSeq(Integer hotSearchSeq) {
        this.hotSearchSeq = hotSearchSeq;
    }

    public Integer getIsOpenKgo() {
        return isOpenKgo;
    }

    public void setIsOpenKgo(Integer isOpenKgo) {
        this.isOpenKgo = isOpenKgo;
    }

    public String getEditUser() {
        return editUser;
    }

    public void setEditUser(String editUser) {
        this.editUser = editUser;
    }

    public Timestamp getEditTime() {
        return editTime;
    }

    public void setEditTime(Timestamp editTime) {
        this.editTime = editTime;
    }
}
