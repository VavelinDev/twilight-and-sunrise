# Twilight and Sunrise Architectures

Hexagonal Architecture in vivid colors.

Twilight Architecture |  Sunrise Architecture
:--------------------:|:---------------------:
![Twilight-Architecture.png](docs%2Fimages%2FTwilight-Architecture.png)  |  ![Sunrise-Architecture.png](docs%2Fimages%2FSunrise-Architecture.png)

# Run it

```shell
./gradlew bootRun
```

Then call the endpoint.
See the [http scripts](./http/TwilightShopApplication.http)

```http request
PUT http://user:password@localhost:8080/carts/active
Content-Type: application/json

{
  "productId": 10, "quantity": 1
}
```