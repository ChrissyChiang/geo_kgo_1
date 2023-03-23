package gov.kcg.kgo.viewModel.mydata.bo;

public class MyDataManifestBO {
    private String filename;
    private String resource_id;
    private String resource_name;
    private String code;

    public MyDataManifestBO(String filename, String resource_id, String resource_name, String code) {
        this.filename = filename;
        this.resource_id = resource_id;
        this.resource_name = resource_name;
        this.code = code;
    }

    public MyDataManifestBO() {
        super();
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getResource_id() {
        return resource_id;
    }

    public void setResource_id(String resource_id) {
        this.resource_id = resource_id;
    }

    public String getResource_name() {
        return resource_name;
    }

    public void setResource_name(String resource_name) {
        this.resource_name = resource_name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
