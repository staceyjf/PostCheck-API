import React from "react";
import styles from "./Searchbar.module.scss";

// define the props
interface SearchbarProps {
  setSearchTerm: (userSearchTerm: string | null) => void;
  placeholder: string;
}

const Searchbar = ({ placeholder, setSearchTerm }: SearchbarProps) => {
  const onSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const form = e.currentTarget;
    const userSearchTerm = new FormData(form).get("search") as string;
    setSearchTerm(userSearchTerm);
  };

  return (
    <div className={styles.searchbar}>
      <form onSubmit={onSubmit} data-testid="search-form">
        <div className={styles.container}>
          <input type="text" placeholder={placeholder} name="search" />
          <button>Search</button>
        </div>
      </form>
    </div>
  );
};

export default Searchbar;
