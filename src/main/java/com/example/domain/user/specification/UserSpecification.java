package com.example.domain.user.specification;

import static com.example.constants.FilteringConstants.FIELD_PASSWORD;

import com.example.base.specification.BaseSpecificationImpl;
import com.example.domain.user.model.User;
import com.example.utils.dto.request.FilteringDTO;
import com.example.utils.service.IMessageService;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

/** Specification for filtering users. */
@Log4j2
@Component
public class UserSpecification extends BaseSpecificationImpl<User> {
    /**
     * Constructor for the UserSpecification class.
     *
     * @param messageService the message service
     */
    public UserSpecification(IMessageService messageService) {
        super(messageService);
    }

    /**
     * Builds a specification.
     *
     * @param filteringDTOList the search parameters
     * @param orderBy the field to order by
     * @param orderDirection the direction to order by
     * @return the specification
     */
    @Override
    public Specification<User> buildSpecification(
            List<FilteringDTO> filteringDTOList, String orderBy, String orderDirection) {
        log.debug("buildSpecification called");

        removeParam(filteringDTOList, FIELD_PASSWORD);

        return super.buildSpecification(filteringDTOList, orderBy, orderDirection);
    }
}
