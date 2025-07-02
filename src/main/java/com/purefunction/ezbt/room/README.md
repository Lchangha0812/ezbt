```java

public void main() {
    AgodaResponse resp = agodaFeignClient.searchHotel();

    switch (resp) {
        case AgodaSuccess s -> s.results().forEach(r -> System.out.println(r.hotelName()));
        case AgodaError e -> log.error("Agoda API Error {} : {}", e.id(), e.message());
    }
}
```
요런거 가능