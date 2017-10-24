# Class architecture overview

Below classes and their associated fields/behaviour to be implemented as an interface.

## Book

### Properties
- Id
- Title
- Cover image
- Purchase date
- Purchase price
- Review
- Rating
- Description
- IsRead
- Genre
- Owned

### Behaviour
- addBook
- removeBook
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