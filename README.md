A cryptocurrency trading system application sample
the structure that was used to create the codebase is :
|-main
  |-java/demo
    |-DTO
      |-Wallet.java
      |-Trade.java
      |-Price.java
    |-Controllers
      |-PriceController.java
      |-TradeController.java
    |-service
      |-PriceService.java
      |-TradeService.java
    |-repository
      |-PriceRepository.java
      |-TradeRepository.java
      |-WalletRepository.java
    |-DataLoader.java
    |-DemoApplication.java
    |-resource
      |-application.properties
|-test
  |-DemoApplicationTests.java

  with the use of maven, dependencies were updated in pom.xml
  used lombok to make easier annoations and connections throughout springboot.


Final outcomes :
as of now the project builds. to test can use ' mvn clean install -DskipTests ' in terminal.   
