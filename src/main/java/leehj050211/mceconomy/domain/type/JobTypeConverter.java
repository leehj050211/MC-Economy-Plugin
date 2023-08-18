package leehj050211.mceconomy.domain.type;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class JobTypeConverter implements AttributeConverter<JobType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(JobType jobType) {
        return jobType.getValue();
    }

    @Override
    public JobType convertToEntityAttribute(Integer dbData) {
        for (JobType jobType : JobType.values()) {
            if (jobType.getValue() == dbData) {
                return jobType;
            }
        }
        throw new IllegalArgumentException("Unknown database value:" + dbData);
    }
}