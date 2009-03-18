PersistentEntityCriteria criteriaForCount = converter.convert(
        new CriteriaTransferObjectCountWrapper(transferObject).wrap(),
        myMappingGroup);

// now you can pass "criteriaForCount" into standard count methods safely
