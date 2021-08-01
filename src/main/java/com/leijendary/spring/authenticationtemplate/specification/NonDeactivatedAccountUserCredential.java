package com.leijendary.spring.authenticationtemplate.specification;

import com.leijendary.spring.authenticationtemplate.model.IamUserCredential;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.OffsetDateTime;
import java.util.ArrayList;

import static com.leijendary.spring.authenticationtemplate.util.PredicateUtil.lowerEqual;
import static javax.persistence.criteria.JoinType.INNER;
import static javax.persistence.criteria.JoinType.LEFT;

@Builder
public class NonDeactivatedAccountUserCredential implements Specification<IamUserCredential> {

    private final String username;
    private final String type;

    @Override
    public Predicate toPredicate(@NonNull final Root<IamUserCredential> root,
                                 @NonNull final CriteriaQuery<?> criteriaQuery,
                                 @NonNull final CriteriaBuilder criteriaBuilder) {
        final var predicates = new ArrayList<Predicate>();

        // User's deactivated status
        final var userJoin = root.join("user", INNER);
        final var userDeactivatedDatePath = userJoin.<OffsetDateTime>get("deactivatedDate");
        final var userNotDeactivated = userDeactivatedDatePath.isNull();

        predicates.add(userNotDeactivated);

        // Account's deactivated status
        final var accountJoin = userJoin.join("account", LEFT);
        final var accountIsNull = accountJoin.isNull();
        final var accountDeactivatedDatePath = accountJoin.<OffsetDateTime>get("deactivatedDate");
        final var accountNotDeactivated = accountDeactivatedDatePath.isNull();
        final var accountIsNullOrNotDeactivated = criteriaBuilder.or(accountIsNull, accountNotDeactivated);

        predicates.add(accountIsNullOrNotDeactivated);

        // Username predicate
        final var usernamePath = root.<String>get("username");
        final var usernameIgnoreCase = lowerEqual(username, usernamePath, criteriaBuilder);

        predicates.add(usernameIgnoreCase);

        // Type predicate
        final var typePath = root.<String>get("type");
        final var typeEqual = criteriaBuilder.equal(typePath, type);

        predicates.add(typeEqual);

        return criteriaQuery
                .where(predicates.toArray(new Predicate[0]))
                .getRestriction();
    }
}
