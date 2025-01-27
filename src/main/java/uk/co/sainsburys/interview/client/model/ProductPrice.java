package uk.co.sainsburys.interview.client.model;

public record ProductPrice(int productUid,
                          double unitPrice,
                          String unitPriceMeasure,
                          int unitPriceMeasureAmount) {
}