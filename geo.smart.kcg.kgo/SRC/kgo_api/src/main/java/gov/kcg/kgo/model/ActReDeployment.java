package gov.kcg.kgo.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "ACT_RE_DEPLOYMENT")
@NamedQuery(name = "ActReDeployment.findAll", query = "SELECT k FROM ActReDeployment k")
public class ActReDeployment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_", unique = true, nullable = false)
    private String id;

    @Column(name = "NAME_")
    private String name;

    @Column(name = "CATEGORY_")
    private String category;

    @Column(name = "KEY_")
    private String key;

    @Column(name = "TENANT_ID_")
    private String tenantId;

    @Column(name = "DEPLOY_TIME_")
    private Timestamp deployTime;

    @Column(name = "ENGINE_VERSION_")
    private String engineVersion;

    @Column(name = "VERSION_")
    private Integer version;

    @Column(name = "PROJECT_RELEASE_VERSION_")
    private String projectReleaseVersion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Timestamp getDeployTime() {
        return deployTime;
    }

    public void setDeployTime(Timestamp deployTime) {
        this.deployTime = deployTime;
    }

    public String getEngineVersion() {
        return engineVersion;
    }

    public void setEngineVersion(String engineVersion) {
        this.engineVersion = engineVersion;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getProjectReleaseVersion() {
        return projectReleaseVersion;
    }

    public void setProjectReleaseVersion(String projectReleaseVersion) {
        this.projectReleaseVersion = projectReleaseVersion;
    }
}
