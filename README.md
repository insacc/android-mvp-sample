# android-mvp-sample

Android MVP Architecture Example

<h3>Summary</h3>

This is a sample for android mvp architecture. It showcases a simple implementation of the Model-View-Presenter pattern with no architectural frameworks. It uses manual dependency injection to provide a repository with local data sources. 


<h3>Key Concepts</h3>

The features are:

-Login <br>
-Register <br>
-AddEditAuction <br>
-Auctions <br>
-AuctionBidList <br>
-AuctionDetail <br>


Each feature has:

A contract defining the view and the presenter<br>
An Activity which is responsible for the creation of fragments and presenters<br>
A Fragment which implements the view interface.<br>
A presenter which implements the presenter interface<br>

In general the business logic lives in the model classes. The presenters mediate between models and views.

# android-mvp-sample
