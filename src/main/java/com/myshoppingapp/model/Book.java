package com.myshoppingapp.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Book")
public class Book extends Product {

	@Column(name = "genre")
	private String genre;

	@Column(name = "authour")
	private String author;

	@Column(name = "publication")
	private String publication;

	public String getGenre() {
		return this.genre;
	}

	public void setGenre(final String genre) {
		this.genre = genre;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(final String author) {
		this.author = author;
	}

	public String getPublication() {
		return this.publication;
	}

	public void setPublication(final String publication) {
		this.publication = publication;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.author == null) ? 0 : this.author.hashCode());
		result = prime * result + ((this.genre == null) ? 0 : this.genre.hashCode());
		result = prime * result + ((this.publication == null) ? 0 : this.publication.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final Book other = (Book) obj;
		if (this.author == null) {
			if (other.author != null) {
				return false;
			}
		} else if (!this.author.equals(other.author)) {
			return false;
		}
		if (this.genre == null) {
			if (other.genre != null) {
				return false;
			}
		} else if (!this.genre.equals(other.genre)) {
			return false;
		}
		if (this.publication == null) {
			if (other.publication != null) {
				return false;
			}
		} else if (!this.publication.equals(other.publication)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Book [genre=" + this.genre + ", author=" + this.author + ", publication=" + this.publication + "]";
	}

}
