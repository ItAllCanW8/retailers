import React, { Component } from 'react';

class Pagination extends Component {
  render() {
    return (
      <nav aria-label="Page navigation example" className="mt-2">
        <ul className="pagination">
          {this.props.currentPage !== 0 && <li className='page-item'>
            <button className='page-link' aria-label='Previous' onClick={() => this.props.toPage(this.props.currentPage-1)}>
              <span aria-hidden='true'>&laquo;</span>
            </button>
          </li>}
          {[...Array(this.props.totalPages)].map((x, i) =>
              <li key={i} className={this.props.currentPage === i ? "page-item active" : "page-item"}>
                <button className="page-link" onClick={() => this.props.toPage(i)}>{i+1}</button>
              </li>
          )}
          {this.props.currentPage+1 !== this.props.totalPages && <li className='page-item'>
            <button className='page-link' aria-label='Next' onClick={() => this.props.toPage(this.props.currentPage+1)}>
              <span aria-hidden='true'>&raquo;</span>
            </button>
          </li>}
        </ul>
      </nav>
    );
  }
}

export default Pagination;