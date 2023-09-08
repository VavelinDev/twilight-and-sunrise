# Twilight and Sunrise Architectures

Depicting Hexagonal architecture in vivid colors.

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