package leehj050211.mceconomy.domain.job.type;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class JobRankTypeConverter implements AttributeConverter<JobRankType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(JobRankType jobRankType) {
        return jobRankType.getValue();
    }

    @Override
    public JobRankType convertToEntityAttribute(Integer dbData) {
        for (JobRankType jobRankType : JobRankType.values()) {
            if (jobRankType.getValue() == dbData) {
                return jobRankType;
            }
        }
        throw new IllegalArgumentException("Unknown database value:" + dbData);
    }
}