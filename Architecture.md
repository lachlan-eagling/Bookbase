# Class architecture overview

Below classes and their associated fields/behaviour to be implemented as an interface.

## Book

### Properties
- Id: int
- Title: String
- Cover image: String (reference to local storage dir)
- Purchase date: Calendar
- Purchase price: float
- Review: Review (should be reference to Review object)
- Rating: int
- Description: String
- IsRead: Boolean
- Genre: Genre
- Owned: Boolean

### Behaviour
- addBook
  Add a book to database, this should be called by constructor when book is created
  
- removeBook
  Remove book from database.
  
- editBook
    - setTitle
    - setCoverImage
    - setPurchaseDate
    - setPurchasePrice
    - addReview
    - editReview
    - setRating
    - setDescription
    - setIsRead
    - setGenre
    - setOwned
- getTitle
- getCoverImage
- getPurchaseDate
- getPurchasePrice
- getReview
- getRating
- getDescription
- getIsRead
- getGenre
- getOwned


## Author

### Properties
- Id
- FirstName
- LastName

### Behaviour
- addAuthor
- removeAuthor
- editAuthor
    - setFirstName
    - setLastName
- getFirstName
- getLastName
- getFullName


## Genre


### Properties
- Id
- Name
- Description

### Behaviour
- addGenre
- removeGenre
- editGenre
    - setName
    - setDescription
- getName
- getDescription


## Wishlist

### Properties
- Books

### Behaviour
- addBookToWishlist
- sortWishlist
    - dateAdded asc/desc
    - title asc/desc
    - rating asc/desc
- removeBookFromWishlist
- bookPurchased