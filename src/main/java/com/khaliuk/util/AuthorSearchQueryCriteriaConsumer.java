package com.khaliuk.util;

import java.util.function.Consumer;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class AuthorSearchQueryCriteriaConsumer implements Consumer<SearchCriteria> {

    private Predicate predicate;
    private CriteriaBuilder criteriaBuilder;
    private Root root;

    public AuthorSearchQueryCriteriaConsumer() {
    }

    public AuthorSearchQueryCriteriaConsumer(
            Predicate predicate,
            CriteriaBuilder criteriaBuilder,
            Root root) {

        this.predicate = predicate;
        this.criteriaBuilder = criteriaBuilder;
        this.root = root;
    }

    @Override
    public void accept(SearchCriteria searchCriteria) {
        if (searchCriteria.getOperation().equalsIgnoreCase(">")) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder
                    .greaterThanOrEqualTo(root.get(searchCriteria.getKey()),
                            searchCriteria.getValue().toString()));
        } else if (searchCriteria.getOperation().equalsIgnoreCase("<")) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder
                    .lessThanOrEqualTo(root.get(searchCriteria.getKey()),
                            searchCriteria.getValue().toString()));
        } else if (searchCriteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(searchCriteria.getKey()).getJavaType() == String.class) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(
                        root.get(searchCriteria.getKey()),
                        "%" + searchCriteria.getValue() + "%"));
            } else {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(
                        root.get(searchCriteria.getKey()), searchCriteria.getValue()));
            }
        }
    }

    public Predicate getPredicate() {
        return predicate;
    }

    public void setPredicate(Predicate predicate) {
        this.predicate = predicate;
    }

    public CriteriaBuilder getCriteriaBuilder() {
        return criteriaBuilder;
    }

    public void setCriteriaBuilder(CriteriaBuilder criteriaBuilder) {
        this.criteriaBuilder = criteriaBuilder;
    }

    public Root getRoot() {
        return root;
    }

    public void setRoot(Root root) {
        this.root = root;
    }
}
