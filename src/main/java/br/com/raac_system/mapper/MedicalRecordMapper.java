package br.com.raac_system.mapper;

import br.com.raac_system.dto.request.MedicalRecordRequest;
import br.com.raac_system.dto.response.MedicalRecordResponse;
import br.com.raac_system.entity.MedicalRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MedicalRecordMapper {

    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "doctor.name", target = "doctorName")
    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "patient.name", target = "patientName")
    @Mapping(target = "notes", ignore = true) // Ignoring as MedicalRecord object might not have "notes" field directly
    MedicalRecordResponse toDto(MedicalRecord record);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "patient", ignore = true) // Setting manually in service
    @Mapping(target = "doctor", ignore = true) // Setting manually in service
    @Mapping(target = "diagnosis", source = "diagnosisJson")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "appointment", ignore = true)
    @Mapping(target = "chiefComplaint", ignore = true)
    @Mapping(target = "physicalExam", ignore = true)
    @Mapping(target = "plan", ignore = true)
    @Mapping(target = "signedAt", ignore = true)
    @Mapping(target = "signatureHash", ignore = true)
    MedicalRecord toEntity(MedicalRecordRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "diagnosis", source = "diagnosisJson")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "appointment", ignore = true)
    @Mapping(target = "chiefComplaint", ignore = true)
    @Mapping(target = "physicalExam", ignore = true)
    @Mapping(target = "plan", ignore = true)
    @Mapping(target = "signedAt", ignore = true)
    @Mapping(target = "signatureHash", ignore = true)
    void updateEntityFromDto(MedicalRecordRequest request, @MappingTarget MedicalRecord record);
}
