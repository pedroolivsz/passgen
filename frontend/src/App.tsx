import { useState } from "react";
import { generatePasswords, type PasswordRequest, type PasswordResponse } from "./services/api";

export default function App() {
    const [result, setResult] = useState<PasswordResponse | null>(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    
}