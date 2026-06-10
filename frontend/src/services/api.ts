const BASE_URL = import.meta.env.VITE_API_URL ?? "http://localhost:8080";

export interface PasswordRequest {
    length: number;
    uppercase: boolean;
    lowercase: boolean;
    numbers: boolean;
    symbols: boolean;
    excludeAmbiguous: boolean;
    quantity: number;
}
