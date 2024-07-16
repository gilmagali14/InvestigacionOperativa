import React from 'react';
import { FaSearch } from 'react-icons/fa';

const SearchBar = ({ setSearch }) => {
  return (
    <div className="input-group shadow-sm">
      <span className="input-group-text bg-white border-end-0">
        <FaSearch className="text-gray-500" />
      </span>
      <input
        type="text"
        className="form-control border-start-0"
        placeholder="Buscar..."
        onChange={(e) => setSearch(e.target.value)}
      />
    </div>
  );
};

export default SearchBar;
