package com.senla.builder.product;

import com.senla.builder.productPart.IProductPart;

public interface IProduct {
    public void installFirstPart(IProductPart iProductPart);
    public void installSecondPart(IProductPart iProductPart);
    public void installThirdPart(IProductPart iProductPart);
}