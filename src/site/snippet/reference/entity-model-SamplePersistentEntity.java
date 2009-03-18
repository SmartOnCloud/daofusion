@Entity
@Table(name = "orders")
public class Order extends OidBasedMutablePersistentEntity {

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Embedded
    private Address shippingAddress;

    @OneToMany(mappedBy = "order")
    @Cascade(value = { CascadeType.SAVE_UPDATE })
    private List<OrderItem> orderItems = new ArrayList<OrderItem>();

    @Column(nullable = false)
    private Boolean complete;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Customer customer;

    protected List<OrderItem> getOrderItems() {
        return orderItems;
    }

    protected void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void removeOrderItem(OrderItem orderItem) {
        orderItems.remove(orderItem);
        orderItem.setOrder(null);
    }

    public List<OrderItem> getUnmodifiableOrderItemList() {
        return Collections.unmodifiableList(orderItems);
    }

    // rest of the getters and setters go here

    public int getTotalPrice() {
        int result = 0;

        for (OrderItem orderItem : orderItems) {
            result += orderItem.getTotalPrice();
        }

        return result;
    }

}
