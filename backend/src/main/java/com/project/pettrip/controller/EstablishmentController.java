package com.project.pettrip.controller;

import com.project.pettrip.dto.EstablishmentInputDTO;
import com.project.pettrip.dto.EstablishmentSummaryDTO;
import com.project.pettrip.service.EstablishmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/establishment")
@Api("Pet Trip Application")
public class EstablishmentController {

    private final EstablishmentService establishmentService;


    public EstablishmentController(EstablishmentService establishmentService) {
        this.establishmentService = establishmentService;
    }

    @ApiOperation("Obter lista de estabelecimentos")
    @ApiResponses({@ApiResponse(code = 200, message = "ok"),
            @ApiResponse(code = 400, message = "\"Oops... Sinto muito. Não foi possível encontrar resultados com " +
                    "as informações que você deseja, tente novamente.\"")})
    @GetMapping
    public Page<EstablishmentSummaryDTO> findWithFilters(EstablishmentInputDTO establishmentInputDTO,
                                                         @PageableDefault(direction = Sort.Direction.ASC, page = 0, size = 6) Pageable pageRequest){
        if (establishmentInputDTO.getCityId() == null){
            EstablishmentInputDTO establishmentInputDTODefault = new EstablishmentInputDTO(1L,
                    establishmentInputDTO.getType(),
                    establishmentInputDTO.getWeight(),
                    establishmentInputDTO.getCastrated(),
                    establishmentInputDTO.getGender());
            return establishmentService.findByFilters(establishmentInputDTODefault, pageRequest);
        }
        return establishmentService.findByFilters(establishmentInputDTO, pageRequest);
    }



    //======= ORIGINAL É ESSE ABAIXO
    /*@ApiOperation("Obter lista de estabelecimentos")
    @ApiResponses({@ApiResponse(code = 200, message = "ok"),
                    @ApiResponse(code = 400, message = "\"Oops... Sinto muito. Não foi possível encontrar resultados com " +
                            "as informações que você deseja, tente novamente com uma localização próxima ao destino.\"")})
    @GetMapping("/search")
    public Page<EstablishmentSummaryDTO> findWithFilters(EstablishmentInputDTO establishmentInputDTO,
                                                                        @PageableDefault(sort = "name", direction = Sort.Direction.ASC, page = 0, size = 6)
                                                         Pageable pageRequest){

        Establishment establishment = Establishment.toEntityEstablishment(establishmentInputDTO);
        Page<Establishment> establishmentsPage = establishmentService.listByFilters(establishment, pageRequest);
        return establishmentsPage.map(Establishment::toEstablishmentSummayDTO);
    }*/


}
