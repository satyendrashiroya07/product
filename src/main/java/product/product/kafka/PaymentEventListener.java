package product.product.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import product.product.dto.ProductRepo;
import product.product.model.Product;
import shiroya.paymentEvent.PaymentEvent;

import java.util.Objects;

@Component
public class PaymentEventListener {

    @Autowired
    private ProductRepo productRepo;

    @KafkaListener(topics = "payment-success1", groupId = "product-events")
    public void handleSuccess(PaymentEvent event) {

        String productId = event.getProductId();

        Product product = productRepo.findByProductId(productId);
        if(Objects.nonNull(product.getQuantity()) && product.getQuantity()>= event.getQuantity()){
            product.setQuantity(product.getQuantity() - event.getQuantity());
        }
        productRepo.save(product);
    }
}


