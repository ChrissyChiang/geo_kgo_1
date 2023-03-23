package gov.kcg.kgo.geoentity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class GeoKgoQuestionnaireMainTopicPK implements Serializable {

    private String questionId; //問卷id
    private String topicId; //題組id

    @Id
    @Column(name = "question_id", columnDefinition = "varchar(30) NOT NULL")
    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    @Id
    @Column(name = "topic_id", columnDefinition = "varchar(30) NOT NULL")
    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    /** 多 PK 的 class 須繼承以下 method **/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeoKgoQuestionnaireMainTopicPK that = (GeoKgoQuestionnaireMainTopicPK) o;
        return Objects.equals(topicId, that.topicId) && Objects.equals(questionId, that.questionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId, topicId);
    }
}
