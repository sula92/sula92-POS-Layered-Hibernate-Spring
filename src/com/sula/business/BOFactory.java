package com.sula.business;

import com.sula.Appinitializer;
import com.sula.business.custom.impl.CustomerBOImpl;
import com.sula.business.custom.impl.ItemBOImpl;
import com.sula.business.custom.impl.OrderBOImpl;

/*there is no need of BOfactory with the spring since Application context work as the factory which can generate any type of
object (DAO,BO etc..)*/

public class BOFactory {

   /* private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getInstance(){
        return (boFactory==null)? (boFactory=new BOFactory()) : boFactory;
    }

    public <T extends SuperBO> T getBO(BOTypes boType){
        switch (boType){
            case ITEM:
                return (T) Appinitializer.ctx.getBean(ItemBOImpl.class);//new ItemBOImpl();
            case ORDER:
                return (T) new OrderBOImpl();
            case CUSTOMER:
                return (T) Appinitializer.ctx.getBean(CustomerBOImpl.class);//new CustomerBOImpl();
            default:
                return null;
        }
    }*/
}
