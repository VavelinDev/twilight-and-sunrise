# Twilight and Sunrise Architectures

**Beautiful and Easily Understandable Landscape of Hexagonal Architecture.**

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white) ![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.2-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white) ![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white) ![Spock](https://img.shields.io/badge/Spock-Framework-E4002B?style=for-the-badge) ![Architecture](https://img.shields.io/badge/Architecture-Hexagonal-8A2BE2?style=for-the-badge)

![GitHub last commit](https://img.shields.io/github/last-commit/VavelinDev/twilight-shop?style=for-the-badge&logo=github) ![GitHub stars](https://img.shields.io/github/stars/VavelinDev/twilight-shop?style=for-the-badge&logo=github) ![GitHub forks](https://img.shields.io/github/forks/VavelinDev/twilight-shop?style=for-the-badge&logo=github) ![GitHub issues](https://img.shields.io/github/issues/VavelinDev/twilight-shop?style=for-the-badge&logo=github)

[//]: # (// @formatter:off)

🌅 Twilight Architecture | 🔆 Sunrise Architecture
:--------------------:|:---------------------:
![Twilight-Architecture.png](docs/images/Twilight-Architecture.png)  |  ![Sunrise-Architecture.png](docs/images/Sunrise-Architecture.png)

[//]: # (// @formatter:on)

*The difference between these two architectures is that, in the Sunrise Architecture, the Domain Layer does not interact
with the Port. This means that the Domain Layer does not declare any Ports in order to connect with the outside world (
i.e., the Infrastructure).*

Table of Contents

<!-- TOC -->
* [Twilight and Sunrise Architectures](#twilight-and-sunrise-architectures)
* [Run it](#run-it)
* [Motivation and Inspiration](#motivation-and-inspiration)
* [Layers (Horizontal Slicing)](#layers-horizontal-slicing)
  * [User Interface (the Sky 🌠)](#user-interface-the-sky-)
  * [Use Case (the Atmosphere 💨)](#use-case-the-atmosphere-)
  * [Domain (The Sun ☀️)](#domain-the-sun-)
  * [Port (The Sea 🌊)](#port-the-sea-)
  * [Infrastructure (The Seabed 🤿)](#infrastructure-the-seabed-)
* [Dependencies](#dependencies)
* [Examples](#examples)
  * [Example of the Twilight Architecture](#example-of-the-twilight-architecture)
  * [Example of the Sunrise Architecture](#example-of-the-sunrise-architecture)
  * [Infrastructure → Top Layers](#infrastructure--top-layers)
  * [Infrastructure → Port](#infrastructure--port)
<!-- TOC -->

# Prerequisites

* **Java 17** or later
* No external database required — the project uses an **H2 in-memory database**

# Run it

The default entry point is `SunriseShopApplication` (the Sunrise variant). Both architectures coexist in the same codebase.

```shell
./gradlew bootRun
```

Then call the endpoint. See the [http scripts](./http/TwilightShopApplication.http)

```http request
PUT http://user:password@localhost:8080/carts/active
Content-Type: application/json

{
  "productId": 1, "quantity": 1
}
```

Expected response: **HTTP 200 OK** with an empty body.

# Motivation and Inspiration

Whereas the Hexagonal Architecture is well-grounded, we still find some room to make things more precise. It is also
relatively hard to imagine the layers in abstract diagrams like Hexagon, Onion, or Port and Adapters (which is, nomen
omen, the best name for the Hexagonal Architecture).

Understanding the interactions and abstractions that occur between layers can be very difficult, especially for those
who are new to Hexagonal Architecture. Furthermore, there is a lack of clear guidelines on how to structure the code.

So, yes, there are two key drivers to make the Hexagonal more pleasant:

* A self-descriptive diagram that nicely depicts layers and how they are stacked.
* Clear definitions of execution and dependency directions.

The presented architectures emphasize the usage of Ports and Adapters (Horizontal Layering) + CQRS (Vertical Slicing)
\+ Domain-Driven Design (at the core / center). Although the main focus of the article is to redefine the Hexagon into
something less abstract.

# Layers (Horizontal Slicing)

From the Sky to the bottom of the Sea.

## User Interface (the Sky 🌠)

* Exposes API to the outside world (REST, SOAP, TCP, etc)
* Sees only the Use Case layer and cannot access the Domain
* Calls use cases using either a direct dependency on a Use Case service or utilizing abstractions (Ports and Adapters
  from the Hexagonal Architecture). The Use Case (business services) usually has only one implementation thus breaking
  them into the implementation and the interface (Port) introduce more complications than benefits (one may remember
  naming convention like *CartService* and *CartServiceImpl*). Twilight and Sunrise Architectures do not impose “ports
  and adapters“ on the driving side (interface side). This decision is on you and here a general rule works - Do you
  need an interface to every Application Service implementation and why?
* Can define Ports (Interfaces) to be satisfied (provided) by the Infrastructure (e.g. Need of a REST call to perform
  some validations, Access a session storage)
* Visible Layers: Use Case
* Example Artifacts: `AddItemToCartCommandEndpoint`(or AddItemToCartController), `AddItemToCartPayloadValidator` (e.g.
  Spring MVC Validators), `AddItemToCartCommandPayload`

## Use Case (the Atmosphere 💨)

* Also known as the Application Service Layer (DDD) or the Command Handler Layer (CQRS)
* Represents a direct definition of the business Use Cases (e.g. *“As a User I want to Add an Item to the Cart so
  that…”*)
* Orchestrates the logic utilizing the Infrastructure (through Ports) and Domain objects (Domain Services and Aggregate
  Roots)
* Can define Ports (Interfaces) to be satisfied (provided) by the Infrastructure
* Visible Layers: Domain
* Example Artifacts: `AddItemToActiveCartCommand`, `AddItemToActiveCartCommandHandler`(in the CQRS, can be also
  AddItemToCartUseCase or AddItemToCartApplicationService)

## Domain (The Sun ☀️)

* Domain as we know it from the DDD - it is the 💛 of the business!
* In the 🌅 Twilight Architecture the Domain is aware of Ports and can communicate with the Infrastructure through them
* In the 🔆 Sunrise Architecture the Domain is not aware of ports. All the communication through ports is moved to the Use
  Case layer. Cleaner version of the Twilight Architecture.
* Can define Ports (Interfaces) to be satisfied (provided) by the Infrastructure **(Twilight Architecture only)**
* Visible Layers: None
* Example Artifacts: `Cart`, `CartItem`, `CartItemAddedEvent` `CartFactory` `CartPriceDomainService`

## Port (The Sea 🌊)

* A requirement / API that must be provided by the infrastructure
* Ports stand for the gates to the Infrastructure world (SQL, Queues, HTTP, etc)
* Is implemented as an interface together with its input / output objects (contract / API objects)
* All layers can demand some help from infrastructure:
  * User Interface - can connect to logging / metrics infrastructure
  * Use Case - wants to load and save domain entities from a repository
  * Domain - could send notifications or call required resources (e.g. repositories). This actions are moved up to the
      User Case level in the Sunrise Architecture. This is the only difference between those two styles - whether the
      Domain can or cannot leverage infrastructure’s capabilities.
* Concrete implementations of ports are delivered by the Infrastructure layer
* Visible Layers: All
* Example Artifacts: `GetNewestPriceListPort` `PersistCartPort` `GetActiveCartPort`

## Infrastructure (The Seabed 🤿)

* Infrastructure implements Ports satisfying requirements declared by the higher layers (above the Port)
* Infrastructure implements all the complexity related to the concrete infrastructure problems (e.g. SQL queries, HTTP
  connections, JMS or Kafka Producers)
* Is not visible by UI, UC and Domain Layers
* Sees and can access all what’s above (objects from UI, UC and Domain)
* Interacts with the higher layers only through the Port
* Visible Layers: User Interface, User Case, Domain
* Example Artifacts: `CartJpa`, `CartRepositoryJpaAdapter` `PriceListRepositoryJpaAdapter` `CartCrudRepository`

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

[//]: # (// @formatter:off)

🌅 Twilight Architecture | 🔆 Sunrise Architecture
:--------------------:|:---------------------:
![Twilight-Architecture](docs/images/Twilight-Architecture.png)  |  ![Sunrise-Architecture](docs/images/Sunrise-Architecture.png)

[//]: # (// @formatter:on)

![Twilight-Architecture](docs/images/Twilight-Infrastructure-To-Port.png)

Nothing crosses the Sea’s surface! The communication with infrastructure (in both ways) goes always through Ports.

3.2. **Infrastructure → Top Layers**: In this style, the Infrastructure Layer depends on the Top Layers. Therefore,
changes in the Top Layers may affect the Infrastructure Layer, but not vice versa (see point 2). In this approach, the
Infrastructure layer does not require a contract/port abstraction to interact with the Top Layers. The “shortcut” is
intentional and conscious. The shortcut does not require producing transfer objects (belong to Port) and assembling
transfer objects to and from the Top Layer’s model.

As a result, for instance, a repository adapter like `CartRepositoryJpaAdapter` queries the database, executes
a `CartFactory`, and returns the `Cart` domain object.

[//]: # (// @formatter:off)

🌅 Twilight Architecture | 🔆 Sunrise Architecture
:--------------------:|:---------------------:
![Twilight-Infrastructure-Dependencies](docs/images/Twilight-Infrastructure-Dependencies.png)  |  ![Sunrise-Infrastructure-Dependencies](docs/images/Sunrise-Infrastructure-Dependencies.png)

[//]: # (// @formatter:on)

![Twilight-Architecture](docs/images/Twilight-Infrastructure-To-Top-Layers.png)

# Examples

In the both first examples the Infrastructure Layer is accessing the `Cart` and `CartFactory` (see pt 3.2.) in order to
construct the object from the repository data and returns it to the Top Layers.

## Example of the Twilight Architecture

In this architecture style Ports can be declared in all the Top Layers including the Domain.
The `CartPriceDomainService` leverages that fact thus it declares the dependency or rather requirement to be fulfilled
by the outside world (the Infrastructure).

![Twilight-Code-Structure](docs/images/Twilight-Code-Structure.png)

### 🟥 User Interface

```java
package com.vavelin.twilight.shop.cart.command.ui;

@RequestMapping("/carts")
public class AddItemToCartCommandEndpoint {
}
```

### 🟧 Use Case

```java
package com.vavelin.twilight.shop.cart.command.usecase;

@CommandHandlerService
public class AddItemToActiveCartCommandHandler implements CommandHandler<AddItemToActiveCartCommand> {

    AddItemToActiveCartCommandHandler(GetActiveCartPort getActiveCartPort,
                                      PersistCartPort persistCartPort,
                                      CartFactory cartFactory,
                                      CartPriceDomainService cartPriceDomainService) {
    }
}

public record AddItemToActiveCartCommand(
    String username,
    Long productId,
    int quantity
) implements Command {
}

public interface PersistCartPort extends Consumer<Cart> {
    void accept(Cart cart);
}

public interface GetActiveCartPort extends Function<String, Optional<Cart>> {
    Optional<Cart> apply(String username);
}

```

### 🟨 Domain

```java
package com.vavelin.twilight.shop.cart.command.domain;

public class Cart {
}

@DomainService
public class CartPriceDomainService {
    /** The Domain Layer requires Port - this part makes the 🌅 Twilight Architecture. */
    private final GetNewestPriceListPort getNewestPriceListPort;

    public CartPrice apply(Cart cart) {
    }
}

/** As stated above - the Port does not have its own separate package 
 *  as it is not a physical layer. */
public interface GetNewestPriceListPort {
    PriceList get();
}
```

### 🟪 Infrastructure

```java
package com.vavelin.twilight.shop.cart.command.infrastructure.jpa;

/** JPA belongs to the Infrastructure and does not leak to the higher Layers! */
public class CartJpa {
}

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
interface CartCrudRepository extends CrudRepository<CartJpa, Long> {
}
```

## Example of the Sunrise Architecture

As mentioned earlier, this architectural style closely resembles the Twilight Architecture, albeit with a significant
distinction. The key difference lies in the relocation of all Ports (in this case, specifically the
`GetNewestPriceListPort`) from the Domain Layer to the Use Case Layer. This strategic shift ensures that the Domain
remains free of external dependencies, promoting a clean and encapsulated design. Consequently, Ports are exclusively
utilized above the Domain Layer, effectively transferring the responsibility of interfacing to the Use Case Layer.

![Sunrise-Code-Structure](docs/images/Sunrise-Code-Structure.png)

Let's see the code that differs.

### 🟧 Use Case

```java
package com.vavelin.twilight.shop.cart.command.usecase;

@CommandHandlerService
public class AddItemToActiveCartCommandHandler implements CommandHandler<AddItemToActiveCartCommand> {

    AddItemToActiveCartCommandHandler(GetActiveCartPort getActiveCartPort,
                                      GetNewestPriceListPort getNewestPriceListPort,
                                      PersistCartPort persistCartPort,
                                      CartFactory cartFactory,
                                      CartPriceDomainService cartPriceDomainService) {
    }
}

public record AddItemToActiveCartCommand(
    String username,
    Long productId,
    int quantity
) implements Command {
}

public interface GetNewestPriceListPort {
    PriceList get();
}

public interface GetActiveCartPort extends Function<String, Optional<Cart>> {
    Optional<Cart> apply(String username);
}

public interface PersistCartPort extends Consumer<Cart> {
    void accept(Cart cart);
}
```

### 🟨 Domain

```java
package com.vavelin.twilight.shop.cart.command.domain;

public class Cart {
}

@DomainService
public class CartPriceDomainService {
    public CartPrice apply(Cart cart, GetNewestPriceListPort.PriceList priceList) {
    }
}

```

One can observe that the port has been shifted from the Domain Service to the Command Handler (Use Case), resulting in a
more streamlined Domain (the core of the system) and more intricate Use Cases (the intelligence of the system). This
strategic shift is a positive development, considering that the Domain is the critical layer that requires meticulous
attention and care.

## Infrastructure → Top Layers

The examples above cover the situation where the Infrastructure Layer accesses objects from the Top Layers. This is
often a conscious shortcut used. As a result, an implementation doesn't require translation objects (API contract) to
communicate between the Infrastructure and Top Layers. However, complexity arises on the Infrastructure side as it
becomes impacted by any changes happening in the Top Layers. The impact comes from the fact that the Infrastructure
implements the Port interfaces which use the Top Layers' objects in their API. This is how:

```java
public interface GetNewestPriceListPort extends Supplier<GetNewestPriceListPort.PriceList> {
    PriceList get();
}

public interface GetActiveCartPort extends Function<String, Optional<Cart>> {
    Optional<Cart> apply(String username);
}

public interface PersistCartPort extends Consumer<Cart> {
    void accept(Cart cart);
}
```

As shown, when the Ports are implemented by the Infrastructure Layer, the Layer takes and returns the domain objects
like `Cart`.

To avoid this situation, the team may decide to introduce special transfer/translation/contract objects that flow
between the Infrastructure and the Top Layers.

## Infrastructure → Port

In order to completely remove the dependency between what is below and above the Sea 🌊, one additional step is required.
The step is to replace all the Top Layer specific objects in Ports with the pure Port API (contract).

Although it isn't visible in the code (the repository), we can define the approach through the below code snippets.

```java
public interface GetNewestPriceListPort extends Supplier<GetNewestPriceListPort.PriceList> {
    PriceList get();

    /** Contract / API */
    record PriceList(Map<Long, Price> priceByProduct) {
    }
}

public interface GetActiveCartPort extends Function<String, Optional<GetActiveCartPort.ReadCartData>> {
    Optional<ReadCartData> apply(String username);

    /** Contract / API */
    record ReadCartData(...) {
    }
}

public interface PersistCartPort extends Consumer<PersistCartPort.CreateCartData> {
    void accept(CreateCartData cart);

    /** Contract / API */
    record CreateCartData(...) {
    }
}
```

Now we have a clear Port API that clearly separates the Infrastructure from the Top Layers. With this maneuver, the
Infrastructure also does not require the `CartFactory` instance, as it doesn't have to create `Cart` anymore.
