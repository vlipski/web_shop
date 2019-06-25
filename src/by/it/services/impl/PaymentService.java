package by.it.services.impl;

import by.it.services.ServiceException;
import by.it.services.UserService;

public class PaymentService {

    private static volatile PaymentService INSTANCE = null;

    public static PaymentService getInstance() {
        PaymentService paymentService = INSTANCE;
        if (paymentService == null) {
            synchronized (UserService.class) {
                paymentService = INSTANCE;
                if (paymentService == null) {
                    INSTANCE = paymentService = new PaymentService();
                }
            }
        }
        return paymentService;
    }

    public boolean payment() {
        int payment = (int) (Math.random() * 3);
        switch (payment) {
            case 0:
                throw new ServiceException("order.insufficientAccount");
            case 1:
                throw new ServiceException("order.paymentSystemError");
            case 2:
                return true;
            case 3:
                return true;
        }
        return true;
    }
}
