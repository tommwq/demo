package main

func main() {
	bookShelf := NewBookShelf(4)
	bookShelf.InsertBook(NewBook("周恩来的晚年岁月"))
	bookShelf.InsertBook(NewBook("C++网络编程"))
	bookShelf.InsertBook(NewBook("J2EE网络编程精解"))
	bookShelf.InsertBook(NewBook("Java编程思想"))

	//获得书架迭代器
	it := bookShelf.Iterator()
	for it.Next() {
		book := it.Get().(*Book)
		println(book.Name)
	}
}

type Iterator interface {
	Next() bool
	Get() interface{}
}

type Iterable interface {
	Iterator() Iterator
}

type Book struct {
	Name string
}

func NewBook(name string) *Book {
	return &Book{Name: name}
}

type BookShelf struct {
	books    []*Book
	nextSlot int
}

func NewBookShelf(capacity int) *BookShelf {
	return &BookShelf{
		books:    make([]*Book, capacity),
		nextSlot: 0,
	}
}

func (r *BookShelf) GetBookFromSlot(index int) *Book {
	if index >= len(r.books) || index < 0 {
		return nil
	}
	return r.books[index]
}

func (r *BookShelf) InsertBook(book *Book) {
	if r.nextSlot == len(r.books) {
		return
	}
	r.books[r.nextSlot] = book
	r.nextSlot++
}

func (r *BookShelf) Size() int {
	return r.nextSlot
}

func (r *BookShelf) Iterator() Iterator {
	return &BookShelfIterator{
		bookShelf: r,
		index:     0,
	}
}

type BookShelfIterator struct {
	bookShelf *BookShelf
	index     int
}

func (r *BookShelfIterator) Next() bool {
	if r.index >= r.bookShelf.Size() {
		return false
	}

	r.index++
	return true
}

func (r *BookShelfIterator) Get() interface{} {
	return r.bookShelf.GetBookFromSlot(r.index - 1)
}
