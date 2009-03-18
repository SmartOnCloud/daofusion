private final CriteriaTransferObject cto = new CriteriaTransferObject();

public void onChangeValue(FilterWidget widget) {
    final String propertyId = widget.getPropertyId();
    final String value = widget.getValue();
    cto.get(propertyId).setFilterValue(value);
}
