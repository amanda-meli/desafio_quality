package com.example.desafio_quality.service;

import com.example.desafio_quality.model.Immobile;
import com.example.desafio_quality.repository.DistrictRepo;
import com.example.desafio_quality.mock.ImmobileDtoMock;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ServiceImmobileTest {

    @InjectMocks
    ServiceImmobile service;

   @Mock
    DistrictRepo repo;

    @BeforeEach
    public void beforeEach() {
        BDDMockito.when(repo.getByName(ArgumentMatchers.any(String.class)))
                .thenReturn(ImmobileDtoMock.getByName("Bairro1"));
    }

    @Test
    void calculateValues() {
        ImmobileDtoMock mock = new ImmobileDtoMock();
        Immobile immobile = service.calculateValues(mock.getImmobileDTO());

        Assertions.assertThat(immobile.getPropName()).isEqualTo(mock.getImmobileDTO().getPropName());
        Assertions.assertThat(immobile.getTotalArea()).isEqualTo(ImmobileDtoMock.getTotalArea());
        Assertions.assertThat(immobile.getTotalValue()).isEqualTo(ImmobileDtoMock.getTotalValue());
        Assertions.assertThat(immobile.getMaxRoom()).isEqualTo(mock.getMaxRoom());

        verify(repo, atLeastOnce()).getByName(mock.getImmobileDTO().getDistrict());

    }
}
