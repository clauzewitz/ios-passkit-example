import React from 'react';
import { render, screen } from '@testing-library/react';
import Passbook from '.';

test('renders learn react link', () => {
    render(<Passbook />);
    const linkElement = screen.getByText(/learn react/i);
    expect(linkElement).toBeInTheDocument();
});
