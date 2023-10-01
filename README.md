# Twilight and Sunrise Architectures

<style>
table, th, td {
   border: none!important;
}
</style>

Hexagonal Architecture in vivid colors.

[//]: # (// @formatter:off)

Twilight Architecture |  Sunrise Architecture
:--------------------:|:---------------------:
![Twilight-Architecture.png](docs%2Fimages%2FTwilight-Architecture.png)  |  ![Sunrise-Architecture.png](docs%2Fimages%2FSunrise-Architecture.png)

[//]: # (// @formatter:on)

The difference between these two architectures is that, in the Sunrise Architecture, the Domain Layer does not interact
with the Port. This means that the Domain Layer does not declare any Ports in order to connect with the outside world (
i.e., the Infrastructure).

# Run it

```shell
./gradlew bootRun
```

Then call the endpoint. See the [http scripts](./http/TwilightShopApplication.http)

```http request
PUT http://user:password@localhost:8080/carts/active
Content-Type: application/json

{
  "productId": 10, "quantity": 1
}
```

# Motivation and Inspiration

Whereas the Hexagonal Architecture is well-grounded, We still find some room to make things more precise. It is also
relatively hard to imagine the layers in abstract diagrams like Hexagon, Onion, or Port and Adapters (which is, nomen
omen, the best name for the Hexagonal Architecture).

Understanding the interactions and abstractions that occur between layers can be very difficult, especially for those
who are new to Hexagonal Architecture. Furthermore, there is a lack of clear guidelines on how to structure the code.

So, yes, there are two key drivers to make the Hexagonal more pleasant:

- A self-descriptive diagram that nicely depicts layers and how they are stacked.
- Clear definitions of execution and dependency directions.

Presented Architectures emphasize usage of Ports and Adapters (Horizontal Layering) + CQRS (Vertical Slicing)+ Domain
Driven Design (in the heart / the Sun)

# Layers

From the Sky to the bottom of the Sea.

## User Interface (the Sky 🌠)

- Exposes API to the outside world (REST, SOAP, TCP, etc)
- Sees only the Use Case layer and cannot access the Domain
- Calls use cases using either a direct dependency on a Use Case service or utilizing abstractions (Ports and Adapters
  from the Hexagonal Architecture). The Use Case (business services) usually has only one implementation thus breaking
  them into the implementation and the interface (Port) introduce more complications than benefits (one may remember
  naming convention like *CartService* and *CartServiceImpl*). Twilight and Sunrise Architectures do not impose “ports
  and adapters“ on the driving side (interface side). This decision is on you and here a general rule works - Do you
  need an interface to every Application Service implementation and why?
- Can define Ports (Interfaces) to be satisfied (provided) by the Infrastructure (e.g. Need of a REST call to perform
  some validations, Access a session storage)
- Visible Layers: Use Case
- Example Artifacts: `AddItemToCartCommandEndpoint`(or AddItemToCartController), `AddItemToCartPayloadValidator` (e.g.
  Spring MVC Validators), `AddItemToCartCommandPayload`

## Use Case (the Atmosphere 💨)

- Also known as the Application Service Layer (DDD) or the Command Handler Layer (CQRS)
- Represents a direct definition of the business Use Cases (e.g. *“As a User I want to Add an Item to the Cart so
  that…”*)
- Orchestrates the logic utilizing the Infrastructure (through Ports) and Domain objects (Domain Services and Aggregate
  Roots)
- Can define Ports (Interfaces) to be satisfied (provided) by the Infrastructure
- Visible Layers: Domain
- Example Artifacts: `AddItemToActiveCartCommand`, `AddItemToActiveCartCommandHandler`(in the CQRS, can be also
  AddItemToCartUseCase or AddItemToCartApplicationService)

## Domain (The Sun ☀️)

- Domain as we know it from the DDD - it is the 💛 of the business!
- In the 🌅 Twilight Architecture the Domain is aware of Ports and can communicate with the Infrastructure through them
- In the 🔆 Sunrise Architecture the Domain is not aware of ports. All the communication though ports is moved to the Use
  Case layer. Cleaner version of the Twilight Architecture.
- Can define Ports (Interfaces) to be satisfied (provided) by the Infrastructure
- Visible Layers: None
- Example Artifacts: `Cart`, `CartItem`, `CartItemAddedEvent` `CartFactory` `CartPriceDomainService`

## Port (The Sea 🌊)

- A requirement / API that must be provided by the infrastructure
- Ports stand for the gates to the Infrastructure world (SQL, Queues, HTTP, etc)
- Is implemented as an interface together with its input / output objects (contract / API objects)
- All layers can demand some help from infrastructure:
    - User Interface - can connect to logging / metrics infrastructure
    - Use Case - wants to load and save domain entities from a repository
    - Domain - could send notifications or call required resources (e.g. repositories). This actions are moved up to the
      User Case level in the Sunrise Architecture. This is the only difference between those two styles - whether the
      Domain can or cannot leverage infrastructure’s capabilities.
- Concrete implementations of ports are delivered by the Infrastructure layer
- Visible Layers: All
- Example Artifacts: `GetNewestPriceListPort` `PersistCartPort` `GetActiveCartPort`

## Infrastructure (The Seabed 🤿)

- Infrastructure implements Ports satisfying requirements declared by the higher layers (above the Port)
- Infrastructure implements all the complexity related to the concrete infrastructure problems (e.g. SQL queries, HTTP
  connections, JMS or Kafka Producers)
- Is not visible by UI, UC and Domain Layers
- Sees and can access all what’s above (objects from UI, UC and Domain)
- Interacts with the higher layers only through the Port
- Visible Layers: User Interface, User Case, Domain
- Example Artifacts: `CartJpa`, `CartRepositoryJpaAdapter` `PriceListRepositoryJpaAdapter` `CartCrudRepository`

# Dependencies

1. In the Top Layers (all above the Port: UI, UC, and Domain), dependencies go from the most external to the most
   internal layer by layer. However, each layer should only directly interact with the layer directly below it, similar
   to the layers of an onion 🧅.
2. **Top Layers → Port:** 💡**Top layers don’t depend on the Infrastructure Layer.** Top Layers define ports (usually
   interfaces and contract objects) if they require some interaction with the wild and unknown underwater world (the
   Infrastructure).
3. There are two main variations for implementing dependencies between the Infrastructure and the Top Layers:

   3.1. **Infrastructure → Port**: This involves making the Port a clear demarcation line where neither the Top Layers
   nor the Infrastructure Layer are aware of each other. In this variation, the Top and Infrastructure Layers interact
   only through the Port. Therefore, the Infrastructure has no dependency on the Top Layers, but only on the Port. This
   is the cleanest approach, and the separation and dependencies look exactly like in the original pictures. This means
   that the dependencies between layers only exist at the points where they contact each other (see below pictures).

<div style="display: flex; flex-direction: row; height: 320pt; justify-content: space-between;">
  <img src="docs/images/Twilight-Architecture.png" style="float: left" alt="twilight-architecture"/>
  <img src="docs/images/Sunrise-Architecture.png" style="float: right" alt="twilight-architecture"/>
</div>

![Twilight-Architecture](docs/images/Twilight-Infrastructure-To-Port.png)

Nothing crosses the Sea’s surface! The communication with infrastructure (in both ways) goes always through Ports.

3.2. **Infrastructure → Top Layers**: In this style, the Infrastructure Layer depends on the Top Layers. Therefore,
changes in the Top Layers may affect the Infrastructure Layer, but not vice versa (see point 2). In this approach, the
Infrastructure layer does not require a contract/port abstraction to interact with the Top Layers. The “shortcut” is
intentional and conscious. The shortcut does not require producing transfer objects (belong to Port) and assembling
transfer objects to and from the Top Layer’s model.

As a result, for instance, a repository adapter like `CartRepositoryJpaAdapter` queries the database, executes
a `CartFactory`, and returns the `Cart` domain object.

<div style="display: flex; flex-direction: row; height: 320pt; justify-content: space-between;">
  <img src="docs/images/Twilight-Infrastructure-Dependencies.png" style="float: left" alt="twilight-architecture"/>
  <img src="docs/images/Sunrise-Infrastructure-Dependencies.png" style="float: right" alt="twilight-architecture"/>
</div>

![Twilight-Architecture](docs/images/Twilight-Infrastructure-To-Top-Layers.png)


## Example of the Twilight Architecture

In this architecture style the Infrastructure Layer is accessing the `Cart` and `CartFactory` in order to construct the object from the repository data and returns it to the Top Layers.

![Twilight-Code-Structure](docs/images/Twilight-Code-Structure.png)

<p style="background-color: tomato; font-weight: bold; padding: 4px">User Interface</p>

```java
package com.vavelin.twilight.shop.cart.command.ui;

@RequestMapping("/carts")
public class AddItemToCartCommandEndpoint {}
```

<p style="background-color: orange; font-weight: bold; padding: 4px">Use Case</p>


```java
package com.vavelin.twilight.shop.cart.command.usecase;

@CommandHandlerService
public class AddItemToActiveCartCommandHandler implements CommandHandler<AddItemToActiveCartCommand> {}

public record AddItemToActiveCartCommand(
    String username,
    Long productId,
    int quantity    
) implements Command {}
```

<p style="background-color: yellow; font-weight: bold; padding: 4px">Domain</p>

```java
package com.vavelin.twilight.shop.cart.command.domain;

public class Cart {}

@DomainService
public class CartPriceDomainService {
    /** The Domain Layer requires Port - this part makes the 🌅 Twilight Architecture. */
    private final GetNewestPriceListPort getNewestPriceListPort;
}

/** As stated above - the Port does not have its own separate package 
 *  as it is no a physical layer. */
public interface GetNewestPriceListPort {}
```

<p style="background-color: violet; font-weight: bold; padding: 4px">Infrastructure</p>

```java
package com.vavelin.twilight.shop.cart.command.infrastructure.jpa;

/** JPA belongs to the Infrastructure and does not leak to the higher Layers! */
public class CartJpa

/** Infrastructure concretize Ports providing an actual implementation. */
@Adapter
public class CartRepositoryJpaAdapter implements GetActiveCartPort, PersistCartPort {
    /** The Infrastructure sees the Domain and uses it.*/
    private final CartFactory cartFactory;
    private final CartCrudRepository cartCrudRepository;

    public CartRepositoryJpaAdapter(CartFactory cartFactory,
                                    CartCrudRepository cartCrudRepository) {
        this.cartFactory = cartFactory;
        this.cartCrudRepository = cartCrudRepository;
    }
}

@Repository
interface CartCrudRepository extends CrudRepository<CartJpa, Long> {}
```

## Example of the Sunrise Architecture

*-- TBD --* 